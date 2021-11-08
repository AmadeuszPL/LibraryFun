package com.amadeusz.library.infrastructure.accounts.librarymembers;

import com.amadeusz.library.application.accounts.Address;
import com.amadeusz.library.application.accounts.Person;
import com.amadeusz.library.application.accounts.librarymembers.LibraryMember;
import com.amadeusz.library.application.accounts.librarymembers.paymentfunctionality.CreditCard;

import java.util.Optional;

public class DefaultLibraryMemberMapper implements LibraryMemberMapper {

    @Override
    public LibraryMemberEntity map(LibraryMember libraryMember) {
        LibraryMemberEntity entity = new LibraryMemberEntity();
        entity.setId(libraryMember.getId());
        entity.setLogin(libraryMember.getLogin());
        entity.setPassword(libraryMember.getPassword());
        entity.setPersonName(libraryMember.getPerson().getName());
        entity.setStreet(libraryMember.getPerson().getAdress().getStreet());
        entity.setCity(libraryMember.getPerson().getAdress().getCity());
        entity.setZipCode(libraryMember.getPerson().getAdress().getZipCode());
        entity.setCountry(libraryMember.getPerson().getAdress().getCountry());
        entity.setPersonEmail(libraryMember.getPerson().getEmail());
        entity.setPersonTelephoneNumber(libraryMember.getPerson().getTelephoneNumber());
        entity.setTotalBooksCheckedOut(libraryMember.getTotalBooksCheckedOut());
        entity.setFine(libraryMember.getFine());
        if (libraryMember.getCard() != null) {
            entity.setCreditCardNumber(libraryMember.getCard().getCardNumber());
            entity.setCardTypeToString(libraryMember.getCard().getCardType().name());
        }
        return entity;
    }

    @Override
    public LibraryMember map(LibraryMemberEntity libraryMemberEntity) {

        Address address = new Address(libraryMemberEntity.getStreet(), libraryMemberEntity.getCity(),
                libraryMemberEntity.getZipCode(),
                libraryMemberEntity.getCountry());

        Person person = new Person(libraryMemberEntity.getPersonName(),
                address,
                libraryMemberEntity.getPersonEmail(),
                libraryMemberEntity.getPersonTelephoneNumber());


        if(libraryMemberEntity.getCreditCardNumber() != 0) {
            CreditCard creditCard =
                    new CreditCard(libraryMemberEntity.getCreditCardNumber());

            return new LibraryMember(libraryMemberEntity.getId(),
                    libraryMemberEntity.getLogin(),
                    libraryMemberEntity.getPassword(), person,
                    libraryMemberEntity.getFine(), creditCard,
                    libraryMemberEntity.getTotalBooksCheckedOut());
        }

        return new LibraryMember(libraryMemberEntity.getId(),
                libraryMemberEntity.getLogin(),
                libraryMemberEntity.getPassword(), person,
                libraryMemberEntity.getFine(),
                libraryMemberEntity.getTotalBooksCheckedOut());
    }

}