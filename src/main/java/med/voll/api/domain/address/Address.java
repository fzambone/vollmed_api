package med.voll.api.domain.address;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Address {

    private String street;
    private String neighborhood;
    private String zipcode;
    private String number;
    private String additional;
    private String city;
    private String state;

    public Address(CreateAddressRequestDto address) {
        this.street = address.street();
        this.neighborhood = address.neighborhood();
        this.zipcode = address.zipcode();
        this.number = address.number();
        this.additional = address.additional();
        this.city = address.city();
        this.state = address.state();
    }

    public Address() {
    }

    public void updateData(CreateAddressRequestDto address) {
        if (address.street() != null) {
            this.street = address.street();
        }
        if (address.neighborhood() != null) {
            this.neighborhood = address.neighborhood();
        }
        if (address.zipcode() != null) {
            this.zipcode = address.zipcode();
        }
        if (address.number() != null) {
            this.number = address.number();
        }
        if (address.additional() != null) {
            this.additional = address.additional();
        }
        if (address.city() != null) {
            this.city = address.city();
        }
        if (address.state() != null) {
            this.state = address.state();
        }
    }
}
