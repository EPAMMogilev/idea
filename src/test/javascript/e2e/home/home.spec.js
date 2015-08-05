'use strict';

var HomePage = require('../home/home.po.js');
var LoginPage = require('../login/login.po.js');

describe('home page test', function() {

	var homePage;
	var loginPage;

	beforeEach(function() {
		homePage = new HomePage();
	});

	function login() {
		loginPage = new LoginPage();
		loginPage.login('first@idea.com', '1234');
	};

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

	it('should open login page after press login button', function() {
		homePage.login();

		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toBe('/login');
		});
	});

	it('should increment idea rating', function() {
		login();
		homePage.like(0);
		expect(homePage.getRating(0)).toBe('32');
	});

	it('should decrement idea rating', function() {
		login();
		homePage.like(0);
		expect(homePage.getRating(0)).toBe('31');
	});
});