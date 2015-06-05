function mapInit(){
	var myMap = new ymaps.Map("map", {
		//[53.894617; 30.331014]
		center: [53.894617, 30.331014],
		zoom: 11,
		controls: ["zoomControl", "fullscreenControl"]
	});

    addMapClickEvent(myMap);
}

function mapInitIdeaDetails(idea){
	var myMap = new ymaps.Map("map", {
		//[53.894617; 30.331014]
		center: [53.894617, 30.331014],
		zoom: 11,
		controls: ["zoomControl", "fullscreenControl"]
	});

    if(idea && idea.latitude && idea.longitude){
        var geoPoint = new ymaps.Placemark([idea.latitude, idea.longitude], null,{
            preset: "islands#redIcon"
        });

        myMap.geoObjects.add(geoPoint);
    }//if

    addMapClickEvent(myMap);
}

function addMapClickEvent(map){

    map.events.add('click', function(e){
    	var coords = e.get('coords');
    	//alert(coords.join(', '));

    	map.geoObjects.removeAll();

        var geoPoint = new ymaps.Placemark([coords[0], coords[1]], null,{
            preset: "islands#redIcon"
        });

        map.geoObjects.add(geoPoint);
    });
}