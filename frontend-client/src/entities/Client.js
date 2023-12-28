export default class Client {
  constructor(id, accountId, creditCardNumber, amount) {
    this.id = id;
    this.accountId = accountId;
    this.creditCardNumber = creditCardNumber;
    this.amount = amount;
  }

  toString() {
    return `Client ID: ${this.accountId}, Credit Card Number: ${this.creditCardNumber}, Amount: ${this.amount}`;
  }

}
