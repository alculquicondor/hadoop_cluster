#!/usr/bin/perl

@files = ('quijote.txt', 'enciclopedia_web.txt', 'biblia_tomo_1.txt', 'biblia_tomo_2.txt', 'biblia_tomo_3.txt');

foreach my $f (@files) {
  print $f.'.. ';
  for my $i (0..9) { 
    system('cd ../input; cp '.$f.' '.$f.'-big.txt; cat '.$f.' >> '.$f.'-big.txt; rm '.$f.'; mv '.$f.'-big.txt '.$f.';');
  }
}
print "OK\n";

