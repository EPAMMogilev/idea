﻿describe('appIdea', function() {
	var homeUrl = 'http://evbymogsd0030.minsk.epam.com:7080/idea';
	
	jasmine.getEnv().defaultTimeoutInterval = 30000;

	//var homeUrl = 'http://localhost:8080/idea';
	browser.get(homeUrl);

	//just !login!
	it('should login', function() {
    		element(by.id('words')).click();
    		element.all(by.id('email')).sendKeys('first@idea.com');
    		element.all(by.id('password')).sendKeys('1234');

    		//login
    		element(by.id('btnLogin')).click();

    		browser.getLocationAbsUrl().then(function(url) {
				expect(url.split('%')[0].split('#')[1]).toBe('/home');
			});
	});

	it('should filter results ideas list after press button - tag1', function() {
		element(by.id('tag1')).click();
		expect(element.all(by.repeater('idea in ideasCtrl.popular')).count()).toEqual(0);
	});
	
	it('should filter results ideas list after press button - tag2', function() {
		element(by.id('tag2')).click();
		expect(element.all(by.repeater('idea in ideasCtrl.popular')).count()).toEqual(3);
	});
	
	it('should filter results ideas list after press button - tag3', function() {
		element(by.id('tag3')).click();
		expect(element.all(by.repeater('idea in ideasCtrl.popular')).count()).toEqual(2);
	});
	
	it('should filter results ideas list after press button - ALL', function() {
		element(by.id('all')).click();
		expect(element.all(by.repeater('idea in ideasCtrl.popular')).count()).toEqual(5);
	});
	
	it('increment rating', function() {
		//get first element by class "vote"
		element.all(by.css(".btn-thumbs")).first().click();
		var postIncRating = element.all(by.id('rating')).first().getText();
		expect(postIncRating).toBe('32');
	});

	it('decrement rating', function() {
		//get second element by class "vote"
		element.all(by.css('.btn-thumbs')).first().click();
		var postIncRating = element.all(by.id('rating')).first().getText();
		expect(postIncRating).toBe('31');
	});
	
	it('should redirect  to ideaDetails page', function() {
		browser.get(homeUrl);
		//get first element by attribute ng-click="details()" (photo)
		element.all(by.css('a[ng-click="details()"]')).first().click();
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toBe('/ideaDetails');
		});
	});
	
	it('should redirect  to ideaDetails page', function() {
		browser.get(homeUrl);
		//get second element by attribute ng-click="details()" (title)
		element.all(by.css('a[ng-click="details()"]')).get(1).click();
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toBe('/ideaDetails');
		});
	});


	it('should open idea details page', function(){
		browser.get(homeUrl);

		element.all(by.id('imgIdeaLogo')).get(2).click();

		//ideaDetails
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toContain('/ideaDetails');
		});
	});

	//TODO: make tests independent
	
	/*it('should open idea update page', function(){
		element(by.id('btnUpdate')).click();

		//ideaDetails
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toContain('/ideaUpdate');
		});
	});

	it('should update data', function(){
		element(by.id('btnInsertUpdate')).click();

		//ideaDetails
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toContain('/home');
		});
	});

	it('should insert new idea', function(){
		element(by.id('addIdeaButton')).click();

		//add new idea page
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toContain('/ideaAddNew');
		});

		//set fields data
		var fldTag = element.all(by.id('searchLine')).get(0);
		var fldCap = element.all(by.id('searchLine')).get(1);
		var fldTxt = element.all(by.id('searchLine')).get(2);
		fldTag.sendKeys('Tag');
		fldCap.sendKeys('Caption');
		fldTxt.sendKeys('Text');

		element(by.id('btnInsertUpdate')).click();

		//ideaInsert
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toContain('/home');
		});
	});*/

});