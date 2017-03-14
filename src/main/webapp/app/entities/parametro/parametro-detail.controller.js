(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('ParametroDetailController', ParametroDetailController);

    ParametroDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Parametro', 'TipoParametro', 'Metodo'];

    function ParametroDetailController($scope, $rootScope, $stateParams, previousState, entity, Parametro, TipoParametro, Metodo) {
        var vm = this;

        vm.parametro = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:parametroUpdate', function(event, result) {
            vm.parametro = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
