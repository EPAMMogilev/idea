describe('appIdea', function() {

	browser.get('http://localhost:8080/idea');

	it('should filter results ideas list after press button - SPORT', function() {
		element(by.id('sport')).click();
		expect(element.all(by.repeater('idea in ideasCtrl.ideas')).count()).toEqual(2);
	});
	
	it('should filter results ideas list after press button - TRANSPORT', function() {
		element(by.id('transport')).click();
		expect(element.all(by.repeater('idea in ideasCtrl.ideas')).count()).toEqual(1);		
	});
	
	it('should filter results ideas list after press button - CULTURE', function() {
		element(by.id('culture')).click();
		expect(element.all(by.repeater('idea in ideasCtrl.ideas')).count()).toEqual(0);		
	});
	
	it('should filter results ideas list after press button - ALL', function() {
		element(by.id('all')).click();
		expect(element.all(by.repeater('idea in ideasCtrl.ideas')).count()).toEqual(3);		
	});
	
	it('increment rating', function() {
		//get first element by class "vote"
		element.all(by.css('.vote')).first().click();
		var postIncRating = element.all(by.id('rating')).first().getText();
		expect(postIncRating).toBe('4');	
	});
	
	it('decrement rating', function() {
		//get second element by class "vote"
		element.all(by.css('.vote')).get(1).click();
		var postIncRating = element.all(by.id('rating')).first().getText();
		expect(postIncRating).toBe('3');	
	});
	
	it('should redirect  to ideaDetails page', function() {
		browser.get('http://localhost:8080/idea');
		//get first element by attribute ng-click="details()" (photo)
		element.all(by.css('a[ng-click="details()"]')).first().click();
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toBe('/ideaDetails');
		});
	});
	
	it('should redirect  to ideaDetails page', function() {
		browser.get('http://localhost:8080/idea');
		//get second element by attribute ng-click="details()" (title)
		element.all(by.css('a[ng-click="details()"]')).get(1).click();
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toBe('/ideaDetails');
		});
	});

});