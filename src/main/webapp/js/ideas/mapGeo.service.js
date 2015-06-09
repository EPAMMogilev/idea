(function() {
'use strict';

angular.module('app.services')
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
            }
       };
};
})();
