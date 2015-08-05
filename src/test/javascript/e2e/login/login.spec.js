'use strict';

var LoginPage = require('../login/login.po.js');

describe('login page test', function() {

	var loginPage;

	beforeEach(function() {
		loginPage = new LoginPage();
	});

	it('should login and redirect to home page', function() {
		loginPage.login('first@idea.com', '1234');

		browser.getLocationAbsUrl().then(function(url) {
            expect(url.split('%')[0].split('#')[1]).toBe('/home');
        });
	});
});