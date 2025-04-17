package co.edu.javeriana.jpa_example2.dto;

public class ErrorDto {
    private String errorString;

    public ErrorDto(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return this.errorString;
    }

}
