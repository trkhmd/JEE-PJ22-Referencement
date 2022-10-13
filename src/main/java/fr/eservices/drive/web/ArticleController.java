package fr.eservices.drive.web;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Category;
import fr.eservices.drive.repository.ArticleRepository;
import fr.eservices.drive.repository.CategoryRepository;
import fr.eservices.drive.web.dto.ArticleEntry;
import fr.eservices.drive.web.dto.CategoryForm;
import fr.eservices.drive.web.dto.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping(path="/articles")
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @ExceptionHandler(DataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String dataExceptionHandler(Exception ex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter w = new PrintWriter( out );
        ex.printStackTrace(w);
        w.close();
        return "ERROR"+"<!--\n" + out.toString() + "\n-->";
    }

    @GetMapping()
    public String getAllArticles(Model model, 
        @RequestParam(name = "cat", required = false) String catFilter,
        @RequestParam(name = "name", required = false) String nameFilter,
        @RequestParam(name = "ref", required = false) String refFilter,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @ModelAttribute("warning") String warning
        )
    {
        PageRequest pageable = new PageRequest(page, size);
        Page<Article> articles;
        List<Category> categories = categoryRepository.findAll();

        // si on a pas de filtres appliqués
        if( (catFilter == null || catFilter.isEmpty()) && (nameFilter == null || nameFilter.isEmpty() ) && (refFilter == null || refFilter.isEmpty() ) ){
            articles = articleRepository.findAll(pageable);
            model.addAttribute("articles", articles);
        } 
        else
        {
            // Pour le filtrage, la priorité est donnée à la référence
            // si on a une référence, on ignore les autres filtres
            refFilter = refFilter.trim();
            if( refFilter.length() > 0 )
            {
                articles = articleRepository.findByEan13(refFilter, pageable);
                model.addAttribute("articles", articles);
            }
            else if( catFilter != null && nameFilter != null )
            {
                // Sinon, on filtre par nom et catégorie
                nameFilter = "%"+nameFilter.trim()+"%";
                Category cat = categoryRepository.findById(catFilter.trim());
                if( cat != null )
                {
                    articles = articleRepository.findByCategoriesAndNameLike(cat, nameFilter, pageable);
                }
                else
                {
                    articles = articleRepository.findByNameLike(nameFilter, pageable);
                }
                model.addAttribute("articles", articles);
            }
        }
        model.addAttribute("categories", categories);
        return "all_articles";
    }

    @GetMapping(path="/edit/{ean13}")
    public String getArticle(@PathVariable(name="ean13") String ean13, Model model, RedirectAttributes atts) {
        String trimmedEan13 = ean13.trim();
        if(!articleRepository.exists(trimmedEan13))
        {
            atts.addFlashAttribute("warning", "Article with EAN: <strong>" + trimmedEan13 + "</strong> does not exist");
            return "redirect:/articles.html";
        }
        Article article = articleRepository.findByEan13(trimmedEan13);
        List<CategoryForm> categories = getCategoriesForm(article.getEan13());
        model.addAttribute("article", article);
        model.addAttribute("categories",categories);
        return "edit_article";
    }

    @PostMapping(path="/edit/{ean13}")
    public String updateArticle(@PathVariable(name="ean13") String ean13, @Valid @ModelAttribute("article") ArticleEntry entry, 
        BindingResult result, Model model ) 
    {
        String trimmedEan13 = ean13.trim();
        if(!articleRepository.exists(trimmedEan13))
            return "redirect:/articles.html";
        model.addAttribute("categories", getCategoriesForm(entry.getEan13()));
        if( result.hasErrors() ) {
            model.addAttribute("error_alert", "Update failed, please check your inputs");
            return "edit_article";
        }
        Article article = createArticleFromArticleEntry(entry);
        articleRepository.save(article);
        model.addAttribute("success_alert", "Article updated successfully");
        return "edit_article";
    }


    /**
     * get all categories and set the selected attribute to true if the article categories is in the categories list
     * @param ean
     * @return
     */
    public List<CategoryForm> getCategoriesForm(String ean) {
        Article article = articleRepository.findByEan13(ean.trim());
        List<Category> categories = categoryRepository.findAll();
        List<CategoryForm> categoriesForm = new ArrayList<>();
        for (Category c : categories) {
            categoriesForm.add(new CategoryForm(c.getId(), c.getName(), article.getCategories().contains(c)));
        }
        return categoriesForm;
    }

    /**
     * Create an article from an article entry
     * @param entry the article entry
     * @return the article
     */
    public Article createArticleFromArticleEntry(ArticleEntry entry) {
        Article article = new Article();
        article.setEan13(entry.getEan13());
        article.setName(entry.getName());
        article.setPrice(entry.getPrice());
        article.setVat(entry.getVat());
        article.setImg(entry.getImg());
        for(String catId : entry.getCategories()) {
            article.getCategories().add(categoryRepository.findById(catId));
        }
        return article;
    }

    @ResponseBody
    @PostMapping(path="/add.json",consumes="application/json")
    public SimpleResponse add(@RequestBody ArticleEntry artEntry) {
        SimpleResponse res = new SimpleResponse();
        if (artEntry.getName() == null ||
                artEntry.getEan13() == null ||
                artEntry.getImg() == null ||
                artEntry.getPrice() <=0 ||
                artEntry.getVat()<= 0){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad request verify entry";
            return res;
        }
        String trimmedEan13 = artEntry.getEan13().trim();
        String trimmedName = artEntry.getName().trim();
        String trimmedImg = artEntry.getImg().trim();
        if(articleRepository.findByEan13(trimmedEan13) != null) {
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Ean13 is already used";
            return res;
        }

        if(trimmedName.length() <3){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Name too short";
            return res;
        }

        if(artEntry.getVat()>100){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad TVA";
            return res;
        }

        if (trimmedEan13.length() != 13){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad ean13:" + trimmedEan13;
            return res;
        }

        //TODO: check category

        Article article = new Article();
        article.setPrice(artEntry.getPrice());
        article.setName(trimmedName);
        //TODO: add categories
        //article.setCategories(artEntry.getCategories());
        article.setImg(trimmedImg);
        article.setEan13(trimmedEan13);
        article.setVat(artEntry.getVat());
        articleRepository.save(article);
        res.status = SimpleResponse.Status.OK;
        res.message = "Article Added ean13: " + trimmedEan13;
        return res;
    }

    @ResponseBody
    @DeleteMapping(path = "/{ean13}")
    public SimpleResponse delete(@PathVariable String ean13) {
        String trimmedEan13 = ean13.trim();
        SimpleResponse res = new SimpleResponse();
        Article article = articleRepository.findByEan13(trimmedEan13);
        if(article == null) {
            res.status = SimpleResponse.Status.ERROR;
            res.message = "article id not found";
            return res;
        }
        articleRepository.delete(article);
        res.status = SimpleResponse.Status.OK;
        res.message = "";
        return res;
    }
}

