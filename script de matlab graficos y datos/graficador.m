%%

% Columnas:
% Number of rectangles(1) - Built tree(2) - Time elapsed(3) - 
% Tree heigth(4) - Number of nodes(5) - Mean(6) - Median(7) -
% Variance(8) - Standard deviation(9) - Disc access search(10) -
% Time search(11) - Total space(12)


L1 = csvread('datos/Linear1.csv',1,0);
L2 = csvread('datos/Linear2.csv',1,0);
G = csvread('datos/Greene.csv',1,0);



%%

M = 100;
m = 40;
N = L1(:,1);
factor = log(N)/log(M);
% for 
% factor = logm(N,M)

%% Linear 2, cantidad de rectangulos vs tiempo de inserción (lineal) (3)
f1 = figure;

plot(N,L2(:,3),'-b',N,L2(:,3)./factor,'--r');
title('Tiempo en inserción de rectángulos con Linear split')
xlabel('Número de rectángulos')
ylabel('Tiempo (milisegundos)')
legend('Tiempo según cantidad','Normalizada')
saveas(f1, 'TiempoInsecionLinear', 'png')

f2 = figure;

loglog(N,L2(:,3),'-b',N,L2(:,3)./factor,'--r');
title('Tiempo en inserción de rectángulos con Linear split (escala logarítmica)')
xlabel('Número de rectángulos')
ylabel('Tiempo (milisegundos)')
legend('Tiempo según cantidad','Normalizada')
saveas(f2, 'TiempoInsecionLinear-Log', 'png')

%% Linear 2, cantidad de rectangulos vs cantidad de nodos (lineal) (5)

f1 = figure;

plot(N,L2(:,5),'-b',N,L2(:,5)./factor,'--r');
title('Cantidad de nodos generados con Linear split')
xlabel('Número de rectángulos')
ylabel('Número de nodos')
legend('Número de nodos','Normalizada')
saveas(f1, 'NumNodosLinear', 'png')

f2 = figure;

loglog(N,L2(:,5),'-b',N,L2(:,5)./factor,'--r');
title('Cantidad de nodos generados con Linear split (escala logarítmica)')
xlabel('Número de rectángulos')
ylabel('Número de nodos')
legend('Número de nodos','Normalizada')
saveas(f2, 'NumNodosLinear-Log', 'png')

%% Linear 2, cantidad de rectangulos vs Cantidad de accesos a disco en búsqueda (lineal) (10)

f1 = figure;

plot(N,L2(:,10),'-b',N,L2(:,10)./factor,'--r');
title('Cantidad de accesos a disco en búsqueda con Linear split')
xlabel('Número de rectángulos')
ylabel('Cantidad de accesos a disco')
legend('Cantidad de accesos a disco','Normalizada')
saveas(f1, 'AccesosDiscoLinear', 'png')

f2 = figure;

loglog(N,L2(:,10),'-b',N,L2(:,10)./factor,'--r');
title('Cantidad de accesos a disco en búsqueda con Linear split (escala logarítmica)')
xlabel('Número de rectángulos')
ylabel('Cantidad de accesos a disco')
legend('Cantidad de accesos a disco','Normalizada')
saveas(f2, 'AccesosDiscoLinear-Log', 'png')

%% Linear 2, cantidad de rectangulos vs tiempo de búsqueda (lineal) (11)

f1 = figure;

plot(N,L2(:,11),'-b',N,L2(:,11)./factor,'--r');
title('Tiempo de búsqueda de rectángulos con Linear split')
xlabel('Número de rectángulos')
ylabel('Tiempo de búsqueda (milisegundos)')
legend('Tiempo de búsqueda','Normalizada')
saveas(f1, 'TiempoBusqLinear', 'png')

f2 = figure;

loglog(N,L2(:,11),'-b',N,L2(:,11)./factor,'--r');
title('Tiempo de búsqueda de rectángulos con Linear split (escala logarítmica)')
xlabel('Número de rectángulos')
ylabel('Tiempo de búsqueda (milisegundos)')
legend('Tiempo de búsqueda','Normalizada')
saveas(f2, 'TiempoBusqLinear-Log', 'png')

%% Linear 2, cantidad de rectangulos vs espacio total (lineal) (12)

f1 = figure;

plot(N,L2(:,12),'-b',N,L2(:,12)./factor,'--r');
title('Espacio total en dico con Linear split')
xlabel('Número de rectángulos')
ylabel('Espacio total (KB)')
legend('Espacio total (KB)','Normalizada')
saveas(f1, 'EspacioLinear', 'png')

f2 = figure;

loglog(N,L2(:,12),'-b',N,L2(:,12)./factor,'--r');
title('Espacio total en dico con Linear split (escala logarítmica)')
xlabel('Número de rectángulos')
ylabel('Espacio total (KB)')
legend('Espacio total (KB)','Normalizada')
saveas(f2, 'EspacioLinear-Log', 'png')


%%
%%
%%




%% Greene, cantidad de rectangulos vs tiempo de inserción (lineal) (3)
f1 = figure;

plot(N,G(:,3),'-b',N,G(:,3)./factor,'--r');
title('Tiempo en inserción de rectángulos con Greene split')
xlabel('Número de rectángulos')
ylabel('Tiempo (milisegundos)')
legend('Tiempo según cantidad','Normalizada')
saveas(f1, 'TiempoInsecionGreene', 'png')

f2 = figure;

loglog(N,G(:,3),'-b',N,G(:,3)./factor,'--r');
title('Tiempo en inserción de rectángulos con Greene split (escala logarítmica)')
xlabel('Número de rectángulos')
ylabel('Tiempo (milisegundos)')
legend('Tiempo según cantidad','Normalizada')
saveas(f2, 'TiempoInsecionGreene-Log', 'png')

%% Linear 2, cantidad de rectangulos vs cantidad de nodos (lineal) (5)

f1 = figure;

plot(N,G(:,5),'-b',N,G(:,5)./factor,'--r');
title('Cantidad de nodos generados con Greene split')
xlabel('Número de rectángulos')
ylabel('Número de nodos')
legend('Número de nodos','Normalizada')
saveas(f1, 'NumNodosGreene', 'png')

f2 = figure;

loglog(N,G(:,5),'-b',N,G(:,5)./factor,'--r');
title('Cantidad de nodos generados con Greene split (escala logarítmica)')
xlabel('Número de rectángulos')
ylabel('Número de nodos')
legend('Número de nodos','Normalizada')
saveas(f2, 'NumNodosGreene-Log', 'png')

%% Linear 2, cantidad de rectangulos vs Cantidad de accesos a disco en búsqueda (lineal) (10)

f1 = figure;

plot(N,G(:,10),'-b',N,G(:,10)./factor,'--r');
title('Cantidad de accesos a disco en búsqueda con Greene split')
xlabel('Número de rectángulos')
ylabel('Cantidad de accesos a disco')
legend('Cantidad de accesos a disco','Normalizada')
saveas(f1, 'AccesosDiscoGreene', 'png')

f2 = figure;

loglog(N,G(:,10),'-b',N,G(:,10)./factor,'--r');
title('Cantidad de accesos a disco en búsqueda con Greene split (escala logarítmica)')
xlabel('Número de rectángulos')
ylabel('Cantidad de accesos a disco')
legend('Cantidad de accesos a disco','Normalizada')
saveas(f2, 'AccesosDiscoGreene-Log', 'png')

%% Linear 2, cantidad de rectangulos vs tiempo de búsqueda (lineal) (11)

f1 = figure;

plot(N,G(:,11),'-b',N,G(:,11)./factor,'--r');
title('Tiempo de búsqueda de rectángulos con Greene split')
xlabel('Número de rectángulos')
ylabel('Tiempo de búsqueda (milisegundos)')
legend('Tiempo de búsqueda','Normalizada')
saveas(f1, 'TiempoBusqGreene', 'png')

f2 = figure;

loglog(N,G(:,11),'-b',N,G(:,11)./factor,'--r');
title('Tiempo de búsqueda de rectángulos con Greene split (escala logarítmica)')
xlabel('Número de rectángulos')
ylabel('Tiempo de búsqueda (milisegundos)')
legend('Tiempo de búsqueda','Normalizada')
saveas(f2, 'TiempoBusqGreene-Log', 'png')

%% Linear 2, cantidad de rectangulos vs espacio total (lineal) (12)

f1 = figure;

plot(N,G(:,12),'-b',N,G(:,12)./factor,'--r');
title('Espacio total en dico con Greene split')
xlabel('Número de rectángulos')
ylabel('Espacio total (KB)')
legend('Espacio total (KB)','Normalizada')
saveas(f1, 'EspacioGreene', 'png')

f2 = figure;

loglog(N,G(:,12),'-b',N,G(:,12)./factor,'--r');
title('Espacio total en dico con Greene split (escala logarítmica)')
xlabel('Número de rectángulos')
ylabel('Espacio total (KB)')
legend('Espacio total (KB)','Normalizada')
saveas(f2, 'EspacioGreene-Log', 'png')

