(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoParametroDetailController', TipoParametroDetailController);

    TipoParametroDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoParametro', 'Parametro'];

    function TipoParametroDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoParametro, Parametro) {
        var vm = this;

        vm.tipoParametro = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:tipoParametroUpdate', function(event, result) {
            vm.tipoParametro = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
