'use strict';

describe('Controller Tests', function() {

    describe('Modulo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockModulo, MockServicio, MockPrueba, MockAmbiente;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockModulo = jasmine.createSpy('MockModulo');
            MockServicio = jasmine.createSpy('MockServicio');
            MockPrueba = jasmine.createSpy('MockPrueba');
            MockAmbiente = jasmine.createSpy('MockAmbiente');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Modulo': MockModulo,
                'Servicio': MockServicio,
                'Prueba': MockPrueba,
                'Ambiente': MockAmbiente
            };
            createController = function() {
                $injector.get('$controller')("ModuloDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smartTestUiApp:moduloUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
