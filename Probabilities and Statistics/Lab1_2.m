x=0:0.1:3;
plot(x, x.^5/10, "--c;5 function;", x, cos(x), "-.g;cos(x);");
title("Some graph");

#figure(1);
#plot(x, x.^5/10, "--k;5 function;");
#figure(2);
#plot(x, cos(x), "--r;cos(x);");
