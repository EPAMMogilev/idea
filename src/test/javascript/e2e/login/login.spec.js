'use strict';

var LoginPage = require('../login/login.po.js');

describe('login page test', function() {

	var loginPage = new LoginPage();

	beforeEach(function() {
		loginPage.getPage();
	});

	it('should login and redirect to home page', function() {
		loginPage.login('first@idea.com', '1234');

		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toBe('/home');
		});
	});
});