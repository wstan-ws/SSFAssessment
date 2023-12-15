package vttp.ssf.assessment.eventmanagement.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EventRegister {
    
    @NotEmpty(message = "This is a required field")
    @Size(min = 5, max = 25, message = "Min 5 characters, Max 25 characters")
    private String fullName;

    @Past(message = "Invalid Birth Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotEmpty(message = "This is a required field")
    @Email(message = "Invalid email format")
    @Size(max = 50, message = "Exceeded maximum of 50 characters")
    private String email;

    @Pattern(regexp = "(8|9)[0-9]{7}", message = "Invalid Phone Number")
    private String mobileNumber;

    private String gender;

    @Min(value = 1, message = "Minimum request value is 1")
    @Max(value = 3, message = "Maximum request value is 3")
    private Integer ticketsRequested;

    public EventRegister() {
    }

    public EventRegister(
            @NotEmpty(message = "This is a required field") @Size(min = 5, max = 25, message = "Min 5 characters, Max 25 characters") String fullName,
            @Past(message = "Invalid Birth Date") Date birthDate,
            @NotEmpty(message = "This is a required field") @Email(message = "Invalid email format") @Size(max = 50, message = "Exceeded maximum of 50 characters") String email,
            @Pattern(regexp = "(8|9)[0-9]{7}", message = "Invalid Phone Number") String mobileNumber, String gender,
            @Min(value = 1, message = "Minimum request value is 1") @Max(value = 3, message = "Maximum request value is 3") Integer ticketsRequested) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.ticketsRequested = ticketsRequested;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getTicketsRequested() {
        return ticketsRequested;
    }

    public void setTicketsRequested(Integer ticketsRequested) {
        this.ticketsRequested = ticketsRequested;
    }

    @Override
    public String toString() {
        return "EventRegister [fullName=" + fullName + ", birthDate=" + birthDate + ", email=" + email
                + ", mobileNumber=" + mobileNumber + ", gender=" + gender + ", ticketsRequested=" + ticketsRequested
                + "]";
    }    
}
