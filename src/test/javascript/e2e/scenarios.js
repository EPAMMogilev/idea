describe('appIdea', function() {

	browser.get('http://evbymogsd0030.minsk.epam.com:7080/idea');

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

});