package com.benchwarmers.grads.grizzlystoreuser;

import com.benchwarmers.grads.grizzlystoreuser.entities.Address;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Address_Repository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Date;

public class GrizzlystoreUserAddressTests {
    @Mock
    private Address_Repository mockedAddressRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Address testAddress = new Address();
//        testAddress.setAddressLine1("123 Fake Street");
//        testAddress.setAddressLine2("N/A");
//        testAddress.setAddressLine3("N/A");
        testAddress.setAddressCity("Melbourne");
        testAddress.setAddressState("VIC");
        testAddress.setAddressPostcode("1234");
        testAddress.setAddressCountry("Australia");
        testAddress.setLastModified(Date.from(Instant.now()));
        Mockito.when(mockedAddressRepository.save( Mockito.any(Address.class) )).thenReturn(testAddress);
    }

    @Test
    public void saveAddress() {
        Address testAddress = mockedAddressRepository.save(new Address());
        Assert.assertNotNull(testAddress);
    }

//    @Test
//    public void addressHasAddressLine1() {
//        Address testAddress = mockedAddressRepository.save(new Address());
//        Assert.assertEquals(testAddress.getAddressLine1(), "123 Fake Street");
//    }
//
//    @Test
//    public void addressHasAddressLine2() {
//        Address testAddress = mockedAddressRepository.save(new Address());
//        Assert.assertEquals(testAddress.getAddressLine2(), "N/A");
//    }
//
//    @Test
//    public void addressHasAddressLine3() {
//        Address testAddress = mockedAddressRepository.save(new Address());
//        Assert.assertEquals(testAddress.getAddressLine3(), "N/A");
//    }

    @Test
    public void addressHasCity() {
        Address testAddress = mockedAddressRepository.save(new Address());
        Assert.assertEquals(testAddress.getAddressCity(), "Melbourne");
    }

    @Test
    public void addressHasPostcode() {
        Address testAddress = mockedAddressRepository.save(new Address());
        Assert.assertEquals(testAddress.getAddressPostcode(), "1234");
    }

    @Test
    public void addressHasCountry() {
        Address testAddress = mockedAddressRepository.save(new Address());
        Assert.assertEquals(testAddress.getAddressCountry(), "Australia");
    }

    @Test
    public void addressHasLastModified() {
        Address testAddress = mockedAddressRepository.save(new Address());
        Assert.assertNotNull(testAddress.getLastModified());
    }
}
