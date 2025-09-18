import * as Switch from "@radix-ui/react-switch";
import { Tooltip, TooltipProvider } from "~/components/ui/tooltip";
import type { Route } from "./+types/project";
import { redirect, useFetcher } from "react-router";
import type React from "react";
import { cloneElement } from "react";
import {
  deleteFlagForProject,
  getAllFlagsForProject,
  updateFlagForProject,
  type FlagParams,
  type UpdateFlagParams,
} from "~/api/flags";
import { authContext } from "~/middleware-context";
import type { Flag } from "~/types";

export async function clientLoader({
  context,
  params,
}: Route.ClientLoaderArgs) {
  const auth = context.get(authContext);
  if (auth === null) {
    throw redirect("/login");
  }

  const result = await getAllFlagsForProject(params.projectId, auth);

  if (result.status === 401) {
    throw redirect("/login");
  }

  if (!result.ok) {
    throw new Response("Failed to load project", { status: result.status });
  }

  const flags: Flag[] = await result.json();
  return flags.sort((a, b) =>
    a.name.toLowerCase().localeCompare(b.name.toLowerCase())
  );
}

async function updateFlag({
  context,
  params,
  request,
}: Route.ClientActionArgs) {
  const auth = context.get(authContext);
  if (auth === null) {
    throw redirect("/login");
  }

  const formData = await request.formData();
  const flagId = formData.get("flagId")?.toString() ?? "";
  const { projectId } = params;
  const updateFlagParams: UpdateFlagParams = { flagId, projectId };

  const enabledEntry = formData.get("enabled");
  if (enabledEntry !== null) {
    updateFlagParams.enabled = enabledEntry.toString() === "true";
  }

  const result = await updateFlagForProject(updateFlagParams, auth);

  if (result.status === 401) {
    throw redirect("/login");
  }

  if (!result.ok) {
    throw new Response("Failed to update flag", { status: result.status });
  }

  const updatedFlag = await result.json();
  return updatedFlag as Flag;
}

async function deleteFlag({
  context,
  params,
  request,
}: Route.ClientActionArgs) {
  const auth = context.get(authContext);
  if (auth === null) {
    throw redirect("/login");
  }

  const formData = await request.formData();
  const flagId = formData.get("flagId")?.toString() ?? "";
  const { projectId } = params;
  const flagParams: FlagParams = { flagId, projectId };

  const result = await deleteFlagForProject(flagParams, auth);

  if (result.status === 401) {
    throw redirect("/login");
  }

  if (!result.ok) {
    throw new Response("Failed to delete flag", { status: result.status });
  }

  const deletedFlag = await result.json();
  return deletedFlag as Flag;
}

export async function clientAction(args: Route.ClientActionArgs) {
  const auth = args.context.get(authContext);
  if (auth === null) {
    throw redirect("/login");
  }

  switch (args.request.method) {
    case "PUT":
      return await updateFlag(args);
    case "DELETE":
      return await deleteFlag(args);
  }
}

export default function Project({ loaderData: flags }: Route.ComponentProps) {
  return (
    <main className="p-8">
      <TooltipProvider>
        <section className="mx-auto max-w-4xl">
          <h1 className="mb-8 text-4xl font-light tracking-tight text-gray-800">
            Kaminel
          </h1>
          <div className="mb-4 flex flex-row items-center justify-between">
            <span className="relative">
              <input
                className="w-sm rounded-sm border border-gray-300 px-3 py-1.5 pl-7 text-sm text-gray-800 placeholder:text-gray-400"
                placeholder="Search for a flag"
              />
              <span className="absolute top-1/2 left-2 -translate-y-1/2 text-gray-400">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth={1.5}
                  stroke="currentColor"
                  className="size-4"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z"
                  />
                </svg>
              </span>
            </span>
            <button className="flex cursor-pointer flex-row items-center justify-center gap-1 rounded-sm bg-orange-500 px-3 py-1.5 text-sm text-white shadow-xs transition-all hover:bg-orange-400 hover:shadow-sm active:bg-orange-600">
              <span>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth={1.5}
                  stroke="currentColor"
                  className="size-4 translate-[0.5px]"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M12 4.5v15m7.5-7.5h-15"
                  />
                </svg>
              </span>
              <span className="tracking-tight">New Flag</span>
            </button>
          </div>
          <div className="overflow-x-auto rounded border border-gray-300">
            <table className="w-full table-fixed divide-y divide-gray-300">
              <thead className="bg-gray-50">
                <tr>
                  <th className="w-2/5 p-4 text-left font-normal tracking-tight">
                    Flag
                  </th>
                  <th className="w-1/5 p-4 text-left font-normal tracking-tight">
                    Key
                  </th>
                  <th className="w-3/20 p-4 text-left font-normal tracking-tight">
                    Enabled
                  </th>
                  <th className="w-1/4 p-4 text-left font-normal tracking-tight">
                    Actions
                  </th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-300">
                {flags.map((flag) => (
                  <FlagRecord key={flag.id} flag={flag} />
                ))}
              </tbody>
            </table>
          </div>
        </section>
      </TooltipProvider>
    </main>
  );
}

function FlagRecord({ flag }: { flag: Flag }) {
  const fetcher = useFetcher();

  const handleCheckedChange = (checked: boolean) => {
    fetcher.submit(
      { flagId: flag.id, enabled: checked },
      { action: `/projects/${flag.projectId}`, method: "PUT" }
    );
  };

  const handleDelete = () => {
    fetcher.submit(
      { flagId: flag.id },
      { action: `/projects/${flag.projectId}`, method: "DELETE" }
    );
  };

  const optimisticEnabled = fetcher.formData?.get("enabled")?.toString();
  const enabled =
    optimisticEnabled !== undefined
      ? optimisticEnabled === "true"
      : flag.enabled;

  return (
    <tr
      data-deleted={fetcher.formMethod === "DELETE"}
      className="data-[deleted=true]:animate-pulse"
    >
      <td className="p-4">
        <div className="space-y-2">
          <div className="text-xl font-semibold tracking-tight text-gray-800">
            {flag.name}
          </div>
          <div className="max-w-full text-sm tracking-tight text-gray-800">
            {flag.description}
          </div>
        </div>
      </td>
      <td className="p-4">
        <span className="rounded-sm bg-orange-100 px-1.5 py-0.5 font-mono text-sm font-semibold text-orange-700">
          {flag.name}
        </span>
      </td>
      <td className="p-4">
        <span className="flex h-full w-full items-center">
          <Switch.Root
            disabled={fetcher.state === "submitting"}
            checked={enabled}
            onCheckedChange={handleCheckedChange}
            className="h-6 w-12 cursor-pointer rounded-full bg-gray-500 inset-shadow-xs transition-colors data-disabled:animate-pulse data-disabled:cursor-wait data-[state=checked]:bg-orange-500"
          >
            <Switch.Thumb className="block size-4 translate-x-1 rounded-full bg-white shadow-sm transition-transform will-change-transform data-disabled:bg-gray-50 data-[state=checked]:translate-x-7" />
          </Switch.Root>
        </span>
      </td>
      <td className="p-4">
        <span className="flex flex-row items-center gap-4">
          <FlagActionButton
            action="Copy URL"
            variant="normal"
            disabled={fetcher.state === "submitting"}
            onClick={() => {
              return;
            }}
            defaultIcon={
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M15.75 17.25v3.375c0 .621-.504 1.125-1.125 1.125h-9.75a1.125 1.125 0 0 1-1.125-1.125V7.875c0-.621.504-1.125 1.125-1.125H6.75a9.06 9.06 0 0 1 1.5.124m7.5 10.376h3.375c.621 0 1.125-.504 1.125-1.125V11.25c0-4.46-3.243-8.161-7.5-8.876a9.06 9.06 0 0 0-1.5-.124H9.375c-.621 0-1.125.504-1.125 1.125v3.5m7.5 10.375H9.375a1.125 1.125 0 0 1-1.125-1.125v-9.25m12 6.625v-1.875a3.375 3.375 0 0 0-3.375-3.375h-1.5a1.125 1.125 0 0 1-1.125-1.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H9.75"
                />
              </svg>
            }
            hoverIcon={
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                fill="currentColor"
              >
                <path d="M7.5 3.375c0-1.036.84-1.875 1.875-1.875h.375a3.75 3.75 0 0 1 3.75 3.75v1.875C13.5 8.161 14.34 9 15.375 9h1.875A3.75 3.75 0 0 1 21 12.75v3.375C21 17.16 20.16 18 19.125 18h-9.75A1.875 1.875 0 0 1 7.5 16.125V3.375Z" />
                <path d="M15 5.25a5.23 5.23 0 0 0-1.279-3.434 9.768 9.768 0 0 1 6.963 6.963A5.23 5.23 0 0 0 17.25 7.5h-1.875A.375.375 0 0 1 15 7.125V5.25ZM4.875 6H6v10.125A3.375 3.375 0 0 0 9.375 19.5H16.5v1.125c0 1.035-.84 1.875-1.875 1.875h-9.75A1.875 1.875 0 0 1 3 20.625V7.875C3 6.839 3.84 6 4.875 6Z" />
              </svg>
            }
          />
          <FlagActionButton
            action="Edit"
            variant="normal"
            disabled={fetcher.state === "submitting"}
            onClick={() => {
              return;
            }}
            defaultIcon={
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"
                />
              </svg>
            }
            hoverIcon={
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                fill="currentColor"
              >
                <path d="M21.731 2.269a2.625 2.625 0 0 0-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 0 0 0-3.712ZM19.513 8.199l-3.712-3.712-8.4 8.4a5.25 5.25 0 0 0-1.32 2.214l-.8 2.685a.75.75 0 0 0 .933.933l2.685-.8a5.25 5.25 0 0 0 2.214-1.32l8.4-8.4Z" />
                <path d="M5.25 5.25a3 3 0 0 0-3 3v10.5a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3V13.5a.75.75 0 0 0-1.5 0v5.25a1.5 1.5 0 0 1-1.5 1.5H5.25a1.5 1.5 0 0 1-1.5-1.5V8.25a1.5 1.5 0 0 1 1.5-1.5h5.25a.75.75 0 0 0 0-1.5H5.25Z" />
              </svg>
            }
          />
          <FlagActionButton
            action="Delete"
            variant="danger"
            disabled={fetcher.state === "submitting"}
            onClick={handleDelete}
            defaultIcon={
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"
                />
              </svg>
            }
            hoverIcon={
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                fill="currentColor"
              >
                <path
                  fillRule="evenodd"
                  d="M16.5 4.478v.227a48.816 48.816 0 0 1 3.878.512.75.75 0 1 1-.256 1.478l-.209-.035-1.005 13.07a3 3 0 0 1-2.991 2.77H8.084a3 3 0 0 1-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 0 1-.256-1.478A48.567 48.567 0 0 1 7.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 0 1 3.369 0c1.603.051 2.815 1.387 2.815 2.951Zm-6.136-1.452a51.196 51.196 0 0 1 3.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 0 0-6 0v-.113c0-.794.609-1.428 1.364-1.452Zm-.355 5.945a.75.75 0 1 0-1.5.058l.347 9a.75.75 0 1 0 1.499-.058l-.346-9Zm5.48.058a.75.75 0 1 0-1.498-.058l-.347 9a.75.75 0 0 0 1.5.058l.345-9Z"
                  clipRule="evenodd"
                />
              </svg>
            }
          />
        </span>
      </td>
    </tr>
  );
}

interface FlagActionButtonProps {
  action: string;
  disabled: boolean;
  defaultIcon: React.ReactElement<React.SVGProps<SVGSVGElement>>;
  hoverIcon: React.ReactElement<React.SVGProps<SVGSVGElement>>;
  variant: "normal" | "danger";
  onClick: React.MouseEventHandler<HTMLButtonElement>;
}

const variantStyles: Record<FlagActionButtonProps["variant"], string> = {
  normal: "text-orange-500",
  danger: "text-red-500",
};

function FlagActionButton(props: FlagActionButtonProps) {
  const baseIconStyles = "absolute top-0 left-0 size-6";
  const disabledIconStyles = "text-gray-600";
  const defaultIconStyles =
    "text-gray-600 opacity-100 group-hover:opacity-0 transition-opacity";
  const hoverIconVisibilityStyles =
    "opacity-0 group-hover:opacity-100 transition-opacity";
  const hoverIconVariantStyles = variantStyles[props.variant];

  const disabledIcon = cloneElement(props.defaultIcon, {
    className: `${baseIconStyles} ${disabledIconStyles} ${props.defaultIcon.props.className ?? ""}`,
  });
  const defaultIcon = cloneElement(props.defaultIcon, {
    className: `${baseIconStyles} ${defaultIconStyles} ${props.defaultIcon.props.className ?? ""}`,
  });
  const hoverIcon = cloneElement(props.hoverIcon, {
    className: `${baseIconStyles} ${hoverIconVisibilityStyles} ${hoverIconVariantStyles} ${props.defaultIcon.props.className ?? ""}`,
  });

  return props.disabled ? (
    <span className="relative size-6 cursor-wait">{disabledIcon}</span>
  ) : (
    <Tooltip content={props.action}>
      <button
        onClick={props.onClick}
        className="group flex cursor-pointer items-center justify-center"
      >
        <span className="sr-only">{props.action}</span>
        <span className="relative size-6">
          {defaultIcon}
          {hoverIcon}
        </span>
      </button>
    </Tooltip>
  );
}
