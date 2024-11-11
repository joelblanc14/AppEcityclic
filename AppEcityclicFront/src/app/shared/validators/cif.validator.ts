import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function cifValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    if (!value) {
      return null;
    }

    // Expressió regular per a la validació del CIF
    const cifPattern = /^[A-Z]\d{7}[A-Z0-9]$/;
    const isValid = cifPattern.test(value);

    return isValid ? null : { cifInvalid: true };
  };
}
