package Parent.domainObject;


public class BillingDetails {
    private String firstname;
    private String lastname;
    private String company;
    private String country;
    private String lastAddressOne;
    private String city;
    private String stateName;
    private String zipCode;
    private String email;
    private String phone;
    private String additionText;

    public BillingDetails(String firstname,
                          String lastname, String company,
                          String country, String lastAddressOne,
                          String city, String stateName,
                          String zipCode, String email,
                          String phone, String additionText)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.company = company;
        this.country = country;
        this.lastAddressOne = lastAddressOne;
        this.city = city;
        this.stateName = stateName;
        this.zipCode = zipCode;
        this.email = email;
        this.phone = phone;
        this.additionText = additionText;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLastAddressOne() {
        return lastAddressOne;
    }

    public void setLastAddressOne(String lastAddressOne) {
        this.lastAddressOne = lastAddressOne;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdditionText() {
        return additionText;
    }

    public void setAdditionText(String additionText) {
        this.additionText = additionText;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}