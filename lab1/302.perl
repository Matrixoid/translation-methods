$pr = 0;

while (<>) {
    
    s/<[^>]*>//g;
    
    if($pr == 0 && !/^[\s]*$/) {
        $pr = 1;
    }
    
    if(!/^[\s]*$/) {
        if($pr == 2) {
            print "\n";
        }
        $pr = 1;
        if(/^[\s]+/) {
	        s/^[\s]+//g;
	    }
	    if(/[\s]+$/) {
	        s/[\s]+$/\n/g;
	    }
	    if(/[\s]{2,}/) {
	        s/[\s]{2,}/ /g;
	    }
    }
	
	if($pr != 0 && /^[\s]*$/) {
	    $pr = 2;
	}
	
	if($pr == 1) {
        s/^[\s]*$/\n/;
        print;
    }
}