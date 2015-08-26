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

	getPage: {value: function() { browser.get('http://evbymogsd0030.minsk.epam.com:7080/idea/#/login'); }}
	//getPage: {value: function() { browser.get('http://localhost:9090/Idea/#/login'); }}
});

module.exports = LoginPage;