function mapInit(){
	var myMap = new ymaps.Map("map", {
		//[53.894617; 30.331014]
		center: [53.894617, 30.331014],
		zoom: 11
	});
}

function mapInitIdeaDetails(idea){
	var myMap = new ymaps.Map("map", {
		//[53.894617; 30.331014]
		center: [53.894617, 30.331014],
		zoom: 11
	});

    if(idea && idea.latitude && idea.longitude){
        var geoPoint = new ymaps.Placemark([idea.latitude, idea.longitude], null,{
            preset: "islands#greenStretchyIcon"
        });

        myMap.geoObjects.add(geoPoint);
    }//if
}