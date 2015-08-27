'use strict';

var HomePage = require('../home/home.po.js');
var LoginPage = require('../login/login.po.js');
var TestFiltersUtility= require('../testFilters-utility.js');

describe('my ideas page test', function() {

	var homePage = new HomePage();
	var loginPage = new LoginPage();

	beforeAll(function() {
		loginPage.getPage();
		loginPage.login('first@idea.com', '1234');
		jasmine.addMatchers({
	        toBeSubstringOfOne: TestFiltersUtility.toBeSubstringOfOne
	    })
	});

	beforeEach(function() {
		homePage.getPage();
		homePage.getMyIdeas();
	});

	it('should open my ideas page', function() {
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toContain('/myideas');
		});
		checkAuthors();
	});

	it('should filter results ideas list after press 3rd tag button', function() {
		TestFiltersUtility.testFilterByTag(homePage, 2);
		checkAuthors();
	});

	it('should filter results ideas list by query in search field', function() {
		var query = 'Wi-Fi';
		TestFiltersUtility.testFilterByQuery(homePage, query);
		checkAuthors();
	});

	it('should filter results ideas list by query in search field after press 3rd tag button', function() {
		var query = 'Wi-Fi';
		var tagId = 2;
		TestFiltersUtility.testFilterByTagAndQuery(homePage, tagId, query);
		checkAuthors();
	});

	function checkAuthors() {
		homePage.authors.count().then(function(count) {
			for(var i = 0; i < count; i++) {
				expect(homePage.getAuthor(i)).toBe('FirstUser');
			}
		});
	}

});