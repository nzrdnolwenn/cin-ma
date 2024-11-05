package models;

import java.time.LocalDate;

public class Customer {
    
    private String name;
    private LocalDate birthDate; 

    public Customer(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public String getName() {
		return name;
	}

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
