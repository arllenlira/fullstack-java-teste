var app = angular.module('empresas', ['ngRoute','ngResource', 'ngGrid', 'ui.bootstrap']);

app.config(function($routeProvider, $locationProvider)
		{
		   // remove o # da url
		   $locationProvider.html5Mode(true);
		 
		   $routeProvider
		 
		   // para a rota '/', carregaremos o template home.html e o controller 'HomeCtrl'
		   .when('/home', {
		      templateUrl : 'notafiscal/app/views/home.html',
		      controller     : 'HomeCtrl',
		   })
		 
		   // para a rota '/sobre', carregaremos o template sobre.html e o controller 'SobreCtrl'
		   .when('/empresa', {
		      templateUrl : 'notafiscal/app/views/empresa.html',
		      controller  : 'EmpresaCtrl',
		   })
		 
		   // para a rota '/contato', carregaremos o template contato.html e o controller 'ContatoCtrl'
		   .when('/contato', {
		      templateUrl : 'notafiscal/app/views/contato.html',
		      controller  : 'ContatoCtrl',
		   })
		 
		   // caso não seja nenhum desses, redirecione para a rota '/'
		   .otherwise ({ redirectTo: '/notafiscal' });
		});

// Create a controller with name empresasListController to bind to the grid section.
app.controller('empresasListController', function ($scope, $rootScope, empresaService) {
    // Initialize required information: sorting, the first page to show and the grid options.
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};
    $scope.empresas = {currentPage: 1};

    $scope.gridOptions = {
        data: 'empresas.list',
        useExternalSorting: true,
        sortInfo: $scope.sortInfo,

        columnDefs: [
            { field: 'id', displayName: 'Id' },
            { field: 'razaoSocial', displayName: 'Razão Social' },
            { field: 'cnpj', displayName: 'CNPJ' },
            { field: 'regimeTributario', displayName: 'Regime Tributário' },
            { field: 'anexos', displayName: 'Anexos' },
            { field: 'email', displayName: 'Email' },
            { field: '', width: 30, cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>' }
        ],

        multiSelect: false,
        selectedItems: [],
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('empresaSelected', $scope.gridOptions.selectedItems[0].id);
            }
        }
    };

    // Refresh the grid, calling the appropriate rest method.
    $scope.refreshGrid = function () {
        var listEmpresasArgs = {
            page: $scope.empresas.currentPage,
            sortFields: $scope.sortInfo.fields[0],
            sortDirections: $scope.sortInfo.directions[0]
        };

        empresaService.get(listEmpresasArgs, function (data) {
            $scope.empresas = data;
        })
    };

    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deleteEmpresa', row.entity.id);
    };

    // Watch the sortInfo variable. If changes are detected than we need to refresh the grid.
    // This also works for the first page access, since we assign the initial sorting in the initialize section.
    $scope.$watch('sortInfo', function () {
        $scope.empresas = {currentPage: 1};
        $scope.refreshGrid();
    }, true);

    // Do something when the grid is sorted.
    // The grid throws the ngGridEventSorted that gets picked up here and assigns the sortInfo to the scope.
    // This will allow to watch the sortInfo in the scope for changed and refresh the grid.
    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
        $scope.sortInfo = sortInfo;
    });

    // Picks the event broadcasted when a empresa is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });

    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    $scope.$on('clear', function () {
        $scope.gridOptions.selectAll(false);
    });
});

// Create a controller with name empresasFormController to bind to the form section.
app.controller('empresasFormController', function ($scope, $rootScope, empresaService) {
    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    $scope.clearForm = function () {
        $scope.empresa = null;
        // For some reason, I was unable to clear field values with type 'url' if the value is invalid.
        // This is a workaroud. Needs proper investigation.
        document.getElementById('anexo').value = null;
        // Resets the form validation state.
        $scope.empresaForm.$setPristine();
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clear');
    };

    // Calls the rest method to save a empresa.
    $scope.updateEmpresa = function () {
        empresaService.save($scope.empresa).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a save message.
                $rootScope.$broadcast('empresaSaved');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    };

    // Picks up the event broadcasted when the empresa is selected from the grid and perform the empresa load by calling
    // the appropiate rest service.
    $scope.$on('empresaSelected', function (event, id) {
        $scope.empresa = empresaService.get({id: id});
    });

    // Picks us the event broadcasted when the empresa is deleted from the grid and perform the actual empresa delete by
    // calling the appropiate rest service.
    $scope.$on('deleteEmpresa', function (event, id) {
        empresaService.delete({id: id}).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a delete message.
                $rootScope.$broadcast('empresaDeleted');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    });
});

// Create a controller with name alertMessagesController to bind to the feedback messages section.
app.controller('alertMessagesController', function ($scope) {
    // Picks up the event to display a saved message.
    $scope.$on('empresaSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record saved successfully!' }
        ];
    });

    // Picks up the event to display a deleted message.
    $scope.$on('empresaDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record deleted successfully!' }
        ];
    });

    // Picks up the event to display a server error message.
    $scope.$on('error', function () {
        $scope.alerts = [
            { type: 'danger', msg: 'There was a problem in the server!' }
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});

// Service that provides empresas operations
app.factory('empresaService', function ($resource) {
    return $resource('resources/empresas/:id');
});



