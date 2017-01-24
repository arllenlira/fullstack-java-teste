app.controller('HomeCtrl', function($rootScope, $location)
{
   $rootScope.activetab = $location.path();
});
 
app.controller('EmpresaCtrl', function($rootScope, $location)
{
   $rootScope.activetab = $location.path();
});
app.controller('NotaFiscalCtrl', function($rootScope, $location)
{
   $rootScope.activetab = $location.path();
});


