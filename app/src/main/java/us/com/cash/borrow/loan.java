package us.com.cash.borrow;

public class loan {

    public String  bankname, bank_number, loan_amount, ifsc_code;
    public String imageURL;

    public loan(String bankname, String bank_number, String loan_amount, String ifsc_code, String imageURL) {
        this.bankname = bankname;
        this.bank_number = bank_number;
        this.loan_amount = loan_amount;
        this.ifsc_code = ifsc_code;
        this.imageURL = imageURL;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBank_number() {
        return bank_number;
    }

    public void setBank_number(String bank_number) {
        this.bank_number = bank_number;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
