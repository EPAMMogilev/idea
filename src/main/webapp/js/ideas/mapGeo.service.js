(function() {
	'use strict';

	angular
		.module('app.services')
		.service('mapGeoService', mapGeoService);
	function mapGeoService() {

		return{
			setGeoCoordsDirective:function(map, coords){
				if(coords){
					var coords = [coords.longitude, coords.latitude];
					map.balloon.open(coords, 'Моя идея');
				}//if
			},//setGeoCoordsDirective
			setGeoCoordsSimpleMap:function(map, coords){
				if(coords){
					var geoPoint = new ymaps.Placemark([coords.longitude, coords.latitude], null,{
						preset: "islands#greenStretchyIcon"
					});

					map.geoObjects.add(geoPoint);
				}//if
			},//setGeoCoordsSimpleMap
			generateGeoObjects:function(ideas){
				var geoObjects = new Array();
				if(ideas){
					for(var i=0; i<ideas.length; i++){
						var oldObject = ideas[i];

						if(oldObject && oldObject.longitude && oldObject.latitude){
							var newGeoObject = {
								geometry: {
									type: 'Point',
									coordinates: [oldObject.longitude,oldObject.latitude]
								},
								properties: {
									balloonContent: oldObject.title,
									clusterCaption: 'идея #' + oldObject.id
								}
							};
							//add 2 array
							geoObjects.push(newGeoObject);
						}//if
					}//for
				}//if
				return geoObjects;
			}//generateGeoObjects
		};
	};
})();