'use strict';

var TestLikesUtility= require('../testLikes-utility.js');

var DetailsPage = require('../ideaDetails/details.po.js');
var HomePage = require('../home/home.po.js');
var LoginPage = require('../login/login.po.js');

describe('details idea page test', function() {

	var detailsPage = new DetailsPage();
	var homePage = new HomePage();
	var loginPage = new LoginPage();

	var title, desc, author, rating;

	beforeAll(function() {
		loginPage.getPage();
		loginPage.login("admin", "admin");
		homePage.getPage();
		title = homePage.getTitle(0);
		desc = homePage.getDescription(0);
		author = homePage.getAuthor(0);
		rating = homePage.getRating(0);
	});

	beforeEach(function() {
		homePage.getPage();
		homePage.ideaDetails(0);
	    jasmine.addMatchers({
	        toBeChangedByOne: TestLikesUtility.toBeChangedByOne
	    });
	});

	it('should open idea update page', function() {
		detailsPage.update();
		expect(browser.getCurrentUrl()).toMatch("/ideaUpdate");
	});

	it('should open idea details page with correct data', function() {
		expect(detailsPage.getTitle()).toBe(title);
		expect(detailsPage.getDesc()).toBe(desc);
		expect(detailsPage.getAuthor()).toBe(author);
		expect(detailsPage.getRating()).toBe(rating);
	});

	it('should increment and decrement idea rating', function() {
		TestLikesUtility.testIncrementAndDecrementRating(detailsPage);
		/*var initialRating = detailsPage.rating;
		detailsPage.like();
		expect(detailsPage.rating).toBeChangedByOne(initialRating);
		detailsPage.like();
		expect(detailsPage.rating).toBe(initialRating);*/
	});


});