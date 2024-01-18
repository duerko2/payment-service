/*package payment.service.aggregate;

import account.service.events.AccountCreated;
import account.service.events.AccountTokenAdded;
import account.service.events.Event;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import lombok.AccessLevel;
import lombok.Getter;

import lombok.Setter;
import messaging.Message;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@AggregateRoot
@Entity
@Getter
public class Account implements Serializable {

	private static final long serialVersionUID = 9023222981284806610L;
	private String name;
	private String lastname;
	private AccountType type;
	private String cpr;
	private String bankId;
	private String accountId;
	List<Token> tokens = new ArrayList<>();


	@Setter(AccessLevel.NONE)
	private List<Event> appliedEvents = new ArrayList<Event>();

	public static Account createAccount(String firstName, String lastName,AccountType type, String cpr) {
		var accountID = new AccountId(UUID.randomUUID());
		AccountCreated event = new AccountCreated(accountID, firstName, lastName,type,cpr);
		var account = new Account();
		account.accountId = accountID;
		account.appliedEvents.add(event);
		return account;
	}

	public static Account createFromEvents(Stream<Event> events) {
		Account account = new Account();
		account.applyEvents(events);
		return account;
	}

	public Account() {
		registerEventHandlers();
	}

	public void UpdateAccountTokens(Set<Token> tokens){
		List<Event> events = new ArrayList<>();

		for (Token token: tokens){
			events.add(new AccountTokenAdded(accountId,token));
		}
		appliedEvents.addAll(events);

	}
	private void registerEventHandlers() {
		handlers.put(AccountCreated.class, e -> apply((AccountCreated) e));
		handlers.put(AccountTokenAdded.class, e -> apply((AccountTokenAdded) e));

	}

	public void clearAppliedEvents() {
		appliedEvents.clear();
	}

	private void apply(AccountCreated event) {
		accountId = event.getAccountId();
		name = event.getName();
		lastname = event.getLastname();
		cpr = event.getCpr();
		type = event.getType();
	}

	private void apply(AccountTokenAdded event){
		tokens.add(event.getToken());
	}

	private void missingHandler(Message e) {
		throw new Error("handler for event "+e+" missing");
	}

	private void applyEvents(Stream<Event> events) throws Error {
		events.forEachOrdered(e -> {
			this.applyEvent(e);
		});
		if (this.getAccountId() == null) {
			throw new Error("user does not exist");
		}
	}
	private void applyEvent(Event e) {
		handlers.getOrDefault(e.getClass(), this::missingHandler).accept(e);
	}


	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	private class AccountType {
		public String type;
	}*/
package payment.service.aggregate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {

	private static final long serialVersionUID = 9023222981284806610L;
	private String name;
	private String lastname;
	private AccountType type;
	private String cpr;
	private String bankId;
	private String accountId;
	List<Token> tokens = new ArrayList<>();

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	private class AccountType {
		public String type;
	}
}


