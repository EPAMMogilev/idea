'use strict';

var HomePage = require('../home/home.po.js');
var TestFiltersUtility= require('../testFilters-utility.js');

describe('filters on home page test', function() {

	var homePage = new HomePage();

	beforeEach(function() {
		homePage.getPage();
	    jasmine.addMatchers({
	        toBeSubstringOfOne: TestFiltersUtility.toBeSubstringOfOne
	    })
	});

	it('should filter results ideas list after press button - all', function() {
		homePage.getAll();
		expect(homePage.popularIdeas.count()).toEqual(5);
		expect(homePage.latestIdeas.count()).toEqual(5);
	});

	it('should filter results ideas list after press 1st tag button', function() {
		TestFiltersUtility.testFilterByTag(homePage, 0);
	});

	it('should filter results ideas list after press 2nd tag button', function() {
		TestFiltersUtility.testFilterByTag(homePage, 1);
	});

	it('should filter results ideas list after press 3rd tag button', function() {
		TestFiltersUtility.testFilterByTag(homePage, 2);
	});

	it('should filter results ideas list by query in search field', function() {
		var query = 'Wi-Fi';
		TestFiltersUtility.testFilterByQuery(homePage, query);
	});

	it('should filter results ideas list by query in search field after press 3rd tag button', function() {
		var query = 'Wi-Fi';
		var tagId = 2;
		TestFiltersUtility.testFilterByTagAndQuery(homePage, tagId, query);
	});

});