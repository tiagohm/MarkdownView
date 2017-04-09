$(document).ready(function() {

	$('abbr').each(function() {
		$(this).data('title', $(this).attr('title'));
		$(this).removeAttr('title');
	});

	$('abbr').mouseover(function() {
	  $('#tooltip').css('display', 'inline-block');
		$('#tooltip').html($(this).data('title'));
    $('#tooltip').css('margin-left', -$('#tooltip').outerWidth() / 2);
	});

	$('abbr').click(function() {
		$(this).mouseover();
		$('#tooltip').animate({opacity: 0.9},{duration: 2000, complete: function() {
			$('#tooltip').fadeOut(1000);
		}});

	});

	$('abbr').mouseout(function() {
		$('#tooltip').css('display', 'none');
	});

});