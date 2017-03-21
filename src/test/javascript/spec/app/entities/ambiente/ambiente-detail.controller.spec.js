'use strict';

describe('Controller Tests', function() {

    describe('Ambiente Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAmbiente, MockPrueba, MockModulo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAmbiente = jasmine.createSpy('MockAmbiente');
            MockPrueba = jasmine.createSpy('MockPrueba');
            MockModulo = jasmine.createSpy('MockModulo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Ambiente': MockAmbiente,
                'Prueba': MockPrueba,
                'Modulo': MockModulo
            };
            createController = function() {
                $injector.get('$controller')("AmbienteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smartTestUiApp:ambienteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
