var LoginPage = function() {};

LoginPage.prototype = Object.create({}, {
	email:      { get: function() { return element(by.id('email')); }},
	password:   { get: function() { return element(by.id('password')); }},
	loginButton:{ get: function() { return element(by.id('btnLogin')); }},

	login: { value: function(email, password) {
		this.email.sendKeys(email);
		this.password.sendKeys(password);
		this.loginButton.click();
	}},

	getPage: {value: function() { browser.get('#/login'); }}
});

module.exports = LoginPage;