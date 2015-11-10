(function () {
    'use strict';

    angular
        .module('app.services')
        .service('mapGeoService', mapGeoService);

    mapGeoService.$inject = ['$translate'];

    function mapGeoService($translate) {

        var myIdea = $translate.instant('MY_IDEA');
        var idea = $translate.instant('IDEA');

        return {
            setGeoCoordsDirective: function (map, coords) {
                if (coords) {
                    var coords = [coords.longitude, coords.latitude];
                    map.balloon.open(coords, myIdea);
                } //if
            }, //setGeoCoordsDirective
            setGeoCoordsSimpleMap: function (map, coords) {
                if (coords) {
                    var geoPoint = new ymaps.Placemark([coords.longitude, coords.latitude], null, {
                        preset: "islands#greenStretchyIcon"
                    });

                    map.geoObjects.add(geoPoint);
                } //if
            }, //setGeoCoordsSimpleMap
            generateGeoObjects: function (ideas) {
                var geoObjects = new Array();
                if (ideas) {
                    for (var i = 0; i < ideas.length; i++) {
                        var oldObject = ideas[i];

                        if (oldObject && oldObject.longitude && oldObject.latitude) {
                            var newGeoObject = {
                                geometry: {
                                    type: 'Point',
                                    coordinates: [oldObject.longitude, oldObject.latitude]
                                },
                                properties: {
                                    balloonContent: oldObject.title,
                                    clusterCaption: idea + ' #' + oldObject.id
                                }
                            };
                            //add 2 array
                            geoObjects.push(newGeoObject);
                        } //if
                    } //for
                } //if
                return geoObjects;
            }, //generateGeoObjects
            getMapCenter: function () {
                return [30.331014, 53.894617];
            }, //getMapCenter
            getMapDefaultZoom: function () {
                    return 11;
                } //getMapDefaultZoom
        };
    };
})();