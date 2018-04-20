'use strict';

exports.testFilterByTag = function (homePage, tagId) {
	homePage.getByTag(tagId);
	var tagText = homePage.getTagText(tagId);
	homePage.ideas.count().then(function(count) {
		for(var i = 0; i < count; i++) {
			expect(homePage.getIdeaTag(i)).toBe(tagText);
		}
	});
}

exports.toBeSubstringOfOne = function() {
    return {
        compare: function(actual, title, description) {
            return { pass: (title.indexOf(actual) !== -1) || (description.indexOf(actual) !== -1) };
        }
    };
}

exports.testFilterByQuery = function (homePage, query) {
	homePage.search(query);
	homePage.ideas.count().then(function(count) {
		for(var i = 0; i < count; i++) {
			expect(query).toBeSubstringOfOne(homePage.getTitle(i), homePage.getDescription(i));
		}
	});
}

exports.testFilterByTagAndQuery = function (homePage, tagId, query) {
	homePage.search(query);
	homePage.ideas.count().then(function(count) {
		for(var i = 0; i < count; i++) {
			expect(query).toBeSubstringOfOne(homePage.getTitle(i), homePage.getDescription(i));
		}
	});
}