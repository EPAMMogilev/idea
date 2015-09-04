'use strict';

var LoginPage = require('../login/login.po.js');
var AccessDeniedPage = require('../accessDenied/accessDenied.po.js');

describe('access denied page test', function() {

	var loginPage = new LoginPage();
	var accessDeniedPage = new AccessDeniedPage();

	beforeAll(function() {
		loginPage.getPage();
		loginPage.login('first@idea.com', '1234');
	});

	it('should redirect to access denied page and return to home after click', function() {
	    browser.setLocation('/ideaUpdate{"id":1}');
	    testLocationAbsUrl('/accessDenied');
		accessDeniedPage.goToHome();
		testLocationAbsUrl('/home');
	});

	function testLocationAbsUrl(absUrl) {
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toContain(absUrl);
		});
	}

});