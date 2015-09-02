'use strict';

var DetailsPage = require('../ideaDetails/details.po.js');
var HomePage = require('../home/home.po.js');
var LoginPage = require('../login/login.po.js');

describe('details idea page test', function() {

	var detailsPage = new DetailsPage();
	var homePage = new HomePage();
	var loginPage = new LoginPage();

	beforeAll(function() {
		loginPage.getPage();
		loginPage.login("admin", "admin");
	});

	beforeEach(function() {
		homePage.getPage();
	});

	it('should open idea update page', function() {
		homePage.ideaDetails(0);
		detailsPage.update();
		expect(browser.getCurrentUrl()).toMatch("/ideaUpdate");
	});

	it('should open idea details page with correct data', function() {
		var title, desc, author, rating;
		title = homePage.getTitle(0);
		desc = homePage.getDescription(0);
		author = homePage.getAuthor(0);
		rating = homePage.getRating(0);
		homePage.ideaDetails(0);
		expect(detailsPage.title).toBe(title);
		expect(detailsPage.desc).toBe(desc);
		expect(detailsPage.author).toBe(author);
		expect(detailsPage.rating).toBe(rating);
	});


});