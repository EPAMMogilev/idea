'use strict';

var FormPage = require('../ideaForm/form.po.js');
var HomePage = require('../home/home.po.js');
var DetailsPage = require('../ideaDetails/details.po.js');
var LoginPage = require('../login/login.po.js');

describe('update idea page test', function() {

	var formPage = new FormPage();
	var homePage = new HomePage();
	var detailsPage = new DetailsPage();
	var loginPage = new LoginPage();

	beforeAll(function() {
		loginPage.getPage();
		loginPage.login("admin", "admin");
	});

	beforeEach(function() {
		homePage.getPage();
		homePage.ideaDetails(5);
		detailsPage.update();
	});

	it('should update idea', function() {
		formPage.updateIdea("New", "New", "New");
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toContain('/home');
		});
	});

	it('should error messages when title and description are empty', function() {
		formPage.clearTitle();
		formPage.clearDesc();
		expect(formPage.titleError.isDisplayed()).toBeTruthy();
		expect(formPage.descError.isDisplayed()).toBeTruthy();
	});

});