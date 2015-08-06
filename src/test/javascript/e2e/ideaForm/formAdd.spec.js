'use strict';

var FormPage = require('../ideaForm/form.po.js');
var HomePage = require('../home/home.po.js');
var LoginPage = require('../login/login.po.js');

describe('add idea page test', function() {

	var formPage = new FormPage();
	var homePage = new HomePage();
	var loginPage = new LoginPage();

	beforeAll(function() {
		loginPage.getPage();
		loginPage.login("admin", "admin");
	});

	beforeEach(function() {
		homePage.getPage();
		homePage.addIdea();
	});

	it('should open add idea page with login and add new idea', function() {
		formPage.addIdea('Tag', 'Caption', 'Text');
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toContain('/home');
		});
	});
});