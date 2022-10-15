package fr.eservices.drive.web.dto;

import fr.eservices.drive.model.Category;

public class CategoryForm {

    Category cat;
    boolean checked;
    
    public CategoryForm(String id, String name, boolean contains) {
        cat = new Category();
        cat.setId(id);
        cat.setName(name);
        checked = contains;
    }

    public Category getCat() {
        return cat;
    }
    
    public boolean isChecked() {
        return checked;
    }
    
    public void setCat(Category cat) {
        this.cat = cat;
    }
    
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "CategoryForm [cat=" + cat + ", checked=" + checked + "]";
    }
}
