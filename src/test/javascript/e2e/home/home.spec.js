'use strict';

var TestLikesUtility= require('../testLikes-utility.js');

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
	    jasmine.addMatchers({
	        toBeChangedByOne: TestLikesUtility.toBeChangedByOne
	    });
	});

	it('should increment and decrement idea rating', function() {
		TestLikesUtility.testIncrementAndDecrementRating(homePage, 0);
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