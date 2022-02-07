package Model;

public class Beneficière {
    String id;
    String phoneNumber;
    String firstName;
    String lastName;
    String account_number;

    public Beneficière(String toString, String toString1, String toString2, String toString3) {
        this.lastName=toString;
        this.firstName=toString1;
        this.account_number=toString2;
        this.phoneNumber=toString3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }
}
