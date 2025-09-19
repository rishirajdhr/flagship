import type React from "react";
import { cn } from "~/utils/ui";

export function Input(props: React.ComponentPropsWithoutRef<"input">) {
  const classname = cn(
    "w-80 rounded border border-gray-300 px-3 py-1.5 text-sm tracking-tight text-gray-800",
    props.className
  );

  return <input {...props} className={classname} />;
}
