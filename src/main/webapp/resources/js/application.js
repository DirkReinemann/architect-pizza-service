function handleTableMessage(data) {	
	var values = data.serverErrorMessage.split(';');
	
	var message = values[1];
	var id = '#deleteMessage_' + values[2];
		
	var obj = $(id);
	obj.html(message);
	obj.css('display', 'inline');
}

function sortIngredients(type) {
	$('#ingredientList > a').each(function(index, value) {
		var o = $(value);
		
		if (type == 'NAME') {
			var name = o.find('.name').html().trim();
			
			
		}
		else if (type == "PRICE") {
			var price = o.find('.price').html().trim();
			price = price.substring(0, price.length - 2);
			
		}
	});
}

function changeIngredientVisibility(categoryItem) {
	if (categoryItem.css('background-color') == 'rgb(210, 210, 210)') {
		categoryItem.css('background-color', '#ffffff');

		var id = categoryItem.attr('id');
		$('.' + id).css('display', 'none');
	}
	else {
		categoryItem.css('background-color', '#d2d2d2');

		var id = categoryItem.attr('id');
		$('.' + id).css('display', 'list-item');
	}
}

function onAddPizzaClicked(data) {
	if (data.status == "suceess") {
		$('#ingredientList > a').each(function(index, value) {
			$(value).css('display', 'inline');
		});
		
		$('#categories > div').each(function(index, value) {
			categoryItem = $(value);
			
			changeIngredientVisibility(categoryItem);
		});
	}
}

function onAddIngredientClicked(data) {
	if (data.status == "success") {
		var id = data.source.id;
		id = id.substring(id.indexOf(':') + 1, id.length);
		id = "#ingredientForm\\:" + id;

		$(id).css('display', 'none');
	}
}

function onRemoveIngredientClicked(data) {
	if (data.status == "success") {
		var id = data.source.parentElement.id;
		id = id.substring(1, id.length);
		id = "#ingredientForm\\:" + id;
		
		$(id).css('display', 'inline');
	}
}

$(document).ready(function() {
	$('#categories > div').click(function() {
		var categoryItem = $(this);
		
		changeIngredientVisibility(categoryItem);
	});
	
	$('#ingredientPizzaList > li').each(function(index, value) {
		var id = $(value).attr('id');
		id = id.slice(1, id.length);
		id = "#ingredientForm\\:" + id;

		$(id).css('display', 'none');
	});
	
	var headerHeight = $('.header').height();
	var container = $('#configurator_right');
	
	$(window).on('scroll', function() {
		var scrollPosition = $(window).scrollTop();
		
		if (scrollPosition > headerHeight) {
			container.css('top', scrollPosition - headerHeight + 'px');
		}
		else {
			container.css('top', 0 + 'px');
		}
	});
});