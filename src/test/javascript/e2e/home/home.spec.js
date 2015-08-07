'use strict';

var HomePage = require('../home/home.po.js');
var LoginPage = require('../login/login.po.js');

describe('home page test', function() {

	var homePage = new HomePage();
	var loginPage = new LoginPage();

	beforeAll(function() {
		loginPage.getPage();
		loginPage.login('admin', 'admin');
	});

	beforeEach(function() {
		homePage.getPage();
	});

	it('should filter results ideas list after press button - all', function() {
		homePage.getAll();
		expect(homePage.popularIdeas.count()).toEqual(5);
		expect(homePage.latestIdeas.count()).toEqual(5);
	});

	it('should filter results ideas list after press 1st tag button', function() {
		homePage.getByTag(0);
		expect(homePage.popularIdeas.count()).toEqual(0);
		expect(homePage.latestIdeas.count()).toEqual(0);
	});

	it('should filter results ideas list after press 2nd tag button', function() {
		homePage.getByTag(1);
		expect(homePage.popularIdeas.count()).toEqual(3);
		expect(homePage.latestIdeas.count()).toEqual(3);
	});

	it('should filter results ideas list after press 3rd tag button', function() {
		homePage.getByTag(2);
		expect(homePage.popularIdeas.count()).toEqual(2);
		expect(homePage.latestIdeas.count()).toEqual(2);
	});

	it('should increment and decrement idea rating', function() {
		homePage.getRating(0).then(function(strRating) {
			var intRating = parseInt(strRating)
			homePage.like(0);
			homePage.getRating(0).then(function(strRatingAfrterFirstClick) {
				var intRatingAfrterFirstClick = parseInt(strRatingAfrterFirstClick);
				expect(Math.abs(intRatingAfrterFirstClick - intRating)).toBe(1);
				homePage.like(0);
				expect(homePage.getRating(0)).toBe(strRating);
			});
		});
	});

	it('should open idea details page (by clicking on image)', function() {
		homePage.ideaDetails(0);
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toContain('/ideaDetails');
		});
	});

	it('should open add idea page with login', function() {
		homePage.addIdea();
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toContain('/ideaAddNew');
		});
	});


});