import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

/**
 * Utility function to merge and conditionally combine Tailwind CSS class names.
 *
 * @param {ClassValue[]} inputs - One or more class names, arrays, or objects representing Tailwind CSS classes.
 * @returns {string} A merged string of class names with conflicts resolved.
 *
 * @example
 * ```
 * const buttonClass = cn(
 *   "px-4 py-2 rounded",
 *   isPrimary ? "bg-blue-500 text-white" : "bg-gray-500 text-black",
 *   isDisabled && "opacity-50 cursor-not-allowed"
 * );
 * ```
 */
export function cn(...inputs: ClassValue[]): string {
  return twMerge(clsx(...inputs));
}
