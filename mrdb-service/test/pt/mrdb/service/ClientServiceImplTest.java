package pt.mrdb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import pt.mrbd.exception.ClientException;
import pt.mrdb.model.Client;

public class ClientServiceImplTest {
	
   private final ClientService clientService = new ClientServiceImpl();
   private Client client;

	@Before
	public void init() {
		client=new Client();
	}
	
//	@Test (expected = Exception.class)
//	public void saveClientWithEmptyNameAndExpectsExceptions() throws ClientException {
//		clientService.save(client);
//	}
//	
//	@Test 
//	public void saveClientWithEmptyName() {
//		try {
//		clientService.save(client);
//	}catch(Exception e) {
//		System.err.println("Error");
//	}
//	}
	
	@Test
	public void saveClientWithEmptyNameAndExpectsExceptionsMessage() {
		String expectedMessage="Client name is mandatory";
		ClientException unauthorizedException = assertThrows(ClientException.class, () -> clientService.save(client));
		assertEquals(unauthorizedException.getMessage(),expectedMessage);
	}
	
	@Test
	public void saveClientWithTwoLettersNameAndExpectsExceptionsMessage() {
		client.setName("Ma");
		String expectedMessage="Client name must have at least 3 letters";
		ClientException unauthorizedException = assertThrows(ClientException.class, () -> clientService.save(client));
		assertEquals(unauthorizedException.getMessage(),expectedMessage);
	}
	
	@Test
	public void saveClientWithEmptyNifAndExpectsExceptionsMessage() {
		client.setName("Mariana");
		String expectedMessage="Client nif is mandatory";
		ClientException unauthorizedException = assertThrows(ClientException.class, () -> clientService.save(client));
		assertEquals(unauthorizedException.getMessage(),expectedMessage);
	}
	
	@Test
	public void saveClientWithEmptyDateOfBithAndExpectsExceptionsMessage() {
		client.setName("Mariana");
		client.setNif("1234");
		String expectedMessage="Client date of birth is mandatory";
		ClientException unauthorizedException = assertThrows(ClientException.class, () -> clientService.save(client));
		assertEquals(unauthorizedException.getMessage(),expectedMessage);
	}
	
	@Test
	public void saveClientWithEmptyEmailAndExpectsExceptionsMessage() {
		client.setName("Mariana");
		client.setNif("1234");
		LocalDate date= LocalDate.of(1990,02, 02);
		client.setDateOfBirth(date);
		String expectedMessage="Client email is mandatory";
		ClientException unauthorizedException = assertThrows(ClientException.class, () -> clientService.save(client));
		assertEquals(unauthorizedException.getMessage(),expectedMessage);
	}
	
	@Test
	public void saveClientWithEmptyPhoneAndExpectsExceptionsMessage() {
		client.setName("Mariana");
		client.setNif("1234");
		LocalDate date= LocalDate.of(1990,02, 02);
		client.setDateOfBirth(date);
		client.setEmail("mari@gmail.com");
		String expectedMessage="Client phone number is mandatory";
		ClientException unauthorizedException = assertThrows(ClientException.class, () -> clientService.save(client));
		assertEquals(unauthorizedException.getMessage(),expectedMessage);
	}
	
	@Test
	public void saveClientWithEmptyPasswordAndExpectsExceptionsMessage() {
		client.setName("Mariana");
		client.setNif("1234");
		LocalDate date= LocalDate.of(1990,02, 02);
		client.setDateOfBirth(date);
		client.setEmail("mari@gmail.com");
		client.setPhone("91919191");
		String expectedMessage="Client password is mandatory";
		ClientException unauthorizedException = assertThrows(ClientException.class, () -> clientService.save(client));
		assertEquals(unauthorizedException.getMessage(),expectedMessage);
	}
	
	
	
	
	
}
	
