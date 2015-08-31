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

	it('should display error messages when try to add idea without description and title', function() {
		formPage.fillTitle('Caption1');
		formPage.clearTitle();
		formPage.fillDesc('Text1');
		formPage.clearDesc();
		expect(formPage.titleError.isDisplayed()).toBeTruthy();
		expect(formPage.descError.isDisplayed()).toBeTruthy();
	});

	it('should display error messages when description and title are dirty but not filled', function() {
		formPage.addIdea('', '', '');
		expect(formPage.titleError.isDisplayed()).toBeTruthy();
	});
});