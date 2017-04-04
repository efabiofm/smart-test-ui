(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('ServicioDialogController', ServicioDialogController);

    ServicioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Servicio', 'Modulo', 'Prueba', 'Metodo'];

    function ServicioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Servicio, Modulo, Prueba, Metodo) {
        var vm = this;

        vm.servicio = entity;
        vm.clear = clear;
        vm.save = save;
        vm.modulos = Modulo.query();
        vm.pruebas = Prueba.query();
        vm.metodos = Metodo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.servicio.id !== null) {
                Servicio.update(vm.servicio, onSaveSuccess, onSaveError);
            } else {
                Servicio.save(vm.servicio, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:servicioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
