package entities;

public class Supplier {
    //tedarikçiye ait özellikler
    private String supplierId;
    private String companyName;
    private String contactEmail;

    //tedarikçiye ait özelliklerin değerleri,parametreler
    public Supplier(String supplierId, String companyName, String contactEmail) {
        this.supplierId = supplierId;
        this.companyName = companyName;
        this.contactEmail = contactEmail;
    }

    //tedarik sınıfının get ve set metodlarını yazıyoruz
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}

