package fr.eservices.drive.web;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Category;
import fr.eservices.drive.repository.ArticleRepository;
import fr.eservices.drive.repository.CategoryRepository;
import fr.eservices.drive.web.dto.ArticleEntry;
import fr.eservices.drive.web.dto.CategoryForm;
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
    public String updateArticle(@PathVariable(name="ean13") String ean13, Model model, RedirectAttributes atts, 
        @ModelAttribute("success_alert") String success_alert) 
    {
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

    @GetMapping(path="/add")
    public String addArticle(Model model) {
        model.addAttribute("article", new ArticleEntry());
        model.addAttribute("categories", categoryRepository.findAll());
        return "add_article";
    }

    @PostMapping(path="/add")
    public String addArticle(@Valid @ModelAttribute("article") ArticleEntry entry, BindingResult result, Model model, 
        RedirectAttributes atts) 
    {
        if( result.hasErrors() ) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("error_alert", "Add failed, please check your inputs");
            return "add_article";
        }
        Article article = createArticleFromArticleEntry(entry);
        if( articleRepository.exists(article.getEan13()) ) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("error_alert", "Add failed, article with EAN: <strong>" + article.getEan13() + "</strong> already exists");
            return "add_article";
        }
        articleRepository.save(article);
        atts.addFlashAttribute("success_alert", "Article added successfully");
        return "redirect:/articles/edit/" + article.getEan13()+".html";
    }


}

